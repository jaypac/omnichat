//import { useQuery } from '@tanstack/react-query';
import { useMutation } from '@tanstack/react-query';
import { SubmitHandler, useForm } from 'react-hook-form';
import MarkdownRenderer from './MarkdownRenderer.tsx';

type Inputs = {
  chatText: string;
};

function Chat() {
  // Queries
  // Queries
  // const { isPending, error, data } = useQuery({
  //   queryKey: ['repoData'],
  //   queryFn: async () => {
  //     const response = await fetch('http://localhost:8080/api/hello');
  //     const data = await response.json();
  //     return data?.message;
  //   },
  // });

  // Mutations
  const { isPending, error, data, mutate } = useMutation({
    mutationFn: async (message: string) => {
      const formData = new URLSearchParams();
      formData.append('message', message);

      const response = await fetch('http://localhost:8080/api/chat', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData,
      });
      const data = await response.json();
      return data?.message;
    },
  });

  console.log(isPending, error, data);

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<Inputs>();

  const onSubmit: SubmitHandler<Inputs> = (data) => mutate(data.chatText);

  console.log(watch('chatText'));
  console.log('errors', errors);

  if (isPending)
    return (
      <div className='spinner-border' role='status'>
        <span className='visually-hidden'>Loading...</span>
      </div>
    );

  if (error)
    return (
      <div className='alert alert-danger' role='alert'>
        error.message
      </div>
    );

  return (
    <>
      <div className='alert alert-light' role='alert'>
        <MarkdownRenderer content={data} />
      </div>
      <form className='needs-validation' onSubmit={handleSubmit(onSubmit)}>
        <div className='mb-3'>
          <label htmlFor='chatText' className='form-label'>
            Enter your message
          </label>
          <input className='form-control' {...register('chatText')} />
          {errors.chatText && (
            <div className='invalid-feedback'>This field is required</div>
          )}
        </div>
        <input type='submit' className='btn btn-primary' />
      </form>
    </>
  );
}

export default Chat;
