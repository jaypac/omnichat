import './App.css';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import Chat from './Chat.tsx';
import { ErrorBoundary } from 'react-error-boundary';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools'

// Create a client
const queryClient = new QueryClient();

function App() {
  return (
    <ErrorBoundary fallback={<div>Something went wrong</div>}>
      <QueryClientProvider client={queryClient}>
        <ReactQueryDevtools initialIsOpen={true} />
        <Chat />
      </QueryClientProvider>
    </ErrorBoundary>
  );
}

export default App;
