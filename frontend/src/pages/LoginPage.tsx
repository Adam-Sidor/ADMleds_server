import React from "react";
import axios from 'axios';

export function LoginPage() {
  const backendIP = 'localhost';

  const login = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const form = event.currentTarget;
    const formData = new FormData(form);

    const username = formData.get('username')?.toString() || '';
    const password = formData.get('password')?.toString() || '';
    try{
      const res = await axios.get('http://' + backendIP + ':8080/api/user/login', {params:{username,password}})
      console.log(res.data);
    }
    catch(error){
      console.error(error);
    }
  };

  return (
    <div>
      <h1>Logowanie</h1>
      <form onSubmit={login}>
        <input type="text" id="username" name="username" placeholder="Username"></input><br></br>
        <input type="password" id="password" name="password" placeholder="Password"></input><br></br>
        <input type="submit"></input>
        <input type="reset"></input>
      </form>
    </div>
  );
}
