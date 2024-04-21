import { useState } from "react";

export default function UserInput() {
  const [userInput, setUserInput] = useState({
    name: '',
    age: 0,
    email: ''
  });

  function handleChange(inputIdentifier, newValue) {
    setUserInput(prev => {
      return {
        ...prev,
        [inputIdentifier]: newValue
      };
    });
  }

  return (
    <div>
      <label>Enter your name:</label>
      <input 
        type='text'
        value={userInput.name}
        onChange={(event) =>
          handleChange('name', event.target.value)
        }
      />
      <br />
      <label>Enter your age:</label>
      <input type='number' />
      <br />
      <label>Enter your email:</label>
      <input type='email' />
      <br />
      <button>Submit</button>
    </div>
  );
}