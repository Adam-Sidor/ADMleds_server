import React, {useState} from "react";
import axios from 'axios';

export function LoginPage({ setIsLogged }: { setIsLogged: (logged: boolean) => void }) {
  const backendIP = 'localhost';

  const [loginStatus, setLoginStatus] = useState("");

  const login = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const form = event.currentTarget;
    const formData = new FormData(form);

    const username = formData.get('username')?.toString() || '';
    const password = formData.get('password')?.toString() || '';
    const staylogged = formData.get('staylogged')?.valueOf() || 0;

    try {
      const res = await axios.post('http://'+backendIP+':8080/api/user/login', { username, password });
      setLoginStatus(res.data.status);
      if(res.data.status==="Zalogowano"){
        if(staylogged){
          localStorage.setItem("token",res.data.token);
        }
        else{
          sessionStorage.setItem("token",res.data.token);
        }
        setIsLogged(true);
      }
    }
    catch (error) {
      console.error(error);
      setLoginStatus("Wystąpił błąd podczas logowania");
    }
  };

  return (
    <div>
      <h1>Logowanie</h1>
      <form onSubmit={login}>
        <input type="text" id="username" name="username" placeholder="Username"></input><br/>
        <input type="password" id="password" name="password" placeholder="Password"></input><br/>
        <input type="checkbox" id="staylogged" name="staylogged"></input>Pozostaw zalogowanym.<br/>
        {loginStatus&&<span>{loginStatus}<br></br></span>}
        <input type="submit"></input>
        <input type="reset"></input>
      </form>
    </div>
  );
}
