package com.omnichat.app.views;

import com.omnichat.app.service.AnthropicChatService;
import com.omnichat.app.service.OpenAIChatService;
import com.omnichat.app.util.MarkdownUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class HomeView extends VerticalLayout {

    private final OpenAIChatService openAIChatService;
    private final AnthropicChatService anthropicChatService;
    private Div resultContent;

    @Autowired
    public HomeView(OpenAIChatService openAIChatService, AnthropicChatService anthropicChatService) {
        this.openAIChatService = openAIChatService;
        this.anthropicChatService = anthropicChatService;

        // Create title
        H1 title = new H1("Welcome to your application");

        // Create text field
        TextField textField = new TextField("Enter your text");
        textField.setWidthFull();

        // Create checkboxes for AI service selection
        Checkbox openAICheckbox = new Checkbox("Open AI");
        Checkbox anthropicCheckbox = new Checkbox("Anthropic");

        // Create a horizontal layout for checkboxes
        HorizontalLayout checkboxesLayout = new HorizontalLayout(openAICheckbox, anthropicCheckbox);
        checkboxesLayout.setSpacing(true);

        // Make checkboxes exclusive (only one can be selected at a time)
        openAICheckbox.addValueChangeListener(event -> {
            if (event.getValue() && anthropicCheckbox.getValue()) {
                anthropicCheckbox.setValue(false);
            }
        });

        anthropicCheckbox.addValueChangeListener(event -> {
            if (event.getValue() && openAICheckbox.getValue()) {
                openAICheckbox.setValue(false);
            }
        });

        // Result area
        Div resultContainer = new Div();
        resultContainer.getStyle().set("margin-top", "1rem");
        resultContainer.getStyle().set("padding", "1rem");
        resultContainer.getStyle().set("border", "1px solid #ccc");
        resultContainer.getStyle().set("overflow", "auto");
        resultContainer.getStyle().set("word-wrap", "break-word");
        resultContainer.getStyle().set("white-space", "normal");
        resultContainer.setWidthFull();

        resultContent = new Div();
        resultContent.getStyle().set("width", "100%");
        resultContainer.add(resultContent);

        // Create submit button
        Button submitButton = new Button("Chat");
        submitButton.addClickListener(event -> {
            String inputText = textField.getValue();

            // Check if at least one checkbox is selected
            if (!openAICheckbox.getValue() && !anthropicCheckbox.getValue()) {
                Notification notification = Notification.show(
                        "Please select either OpenAI or Anthropic before submitting",
                        3000,
                        Notification.Position.MIDDLE
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            // Process based on selected AI service
            String result;
            if (openAICheckbox.getValue()) {
                result = openAIChatService.chat(inputText);
            } else {
                result = anthropicChatService.chat(inputText);
            }

            // Convert markdown to HTML and display
            String htmlContent = MarkdownUtil.convertMarkdownToHtml(result);
            resultContent.getElement().setProperty("innerHTML", htmlContent);

        });

        // Configure layout
        setSpacing(true);
        setPadding(true);

        // Add components
        add(title, textField, checkboxesLayout, submitButton, resultContainer);
    }
}