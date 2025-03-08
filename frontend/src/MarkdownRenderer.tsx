import ReactMarkdown from 'react-markdown';

function MarkdownRenderer(props: { content: string }) {
  return <ReactMarkdown>{props.content}</ReactMarkdown>;
}

export default MarkdownRenderer;
