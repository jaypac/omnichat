package com.omnichat.app.views;

import com.omnichat.app.service.HelloService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class HomeView extends VerticalLayout {

    private final HelloService helloService;

    @Autowired
    public HomeView(HelloService helloService) {
        this.helloService = helloService;

        // Create title
        H1 title = new H1("Welcome to your application");

        // Create text field
        TextField textField = new TextField("Enter your text");
        textField.setWidthFull();

        // Result area
        Div resultArea = new Div();
        resultArea.getStyle().set("margin-top", "1rem");
        resultArea.getStyle().set("padding", "1rem");
        resultArea.getStyle().set("border", "1px solid #ccc");
        resultArea.setWidthFull();

        // Create submit button
        Button submitButton = new Button("Submit");
        submitButton.addClickListener(event -> {
            String inputText = textField.getValue();
            String result = helloService.getHelloWorld(inputText);
            resultArea.setText(result);
        });

        // Configure layout
        setSpacing(true);
        setPadding(true);

        // Add components
        add(title, textField, submitButton, resultArea);
    }
}