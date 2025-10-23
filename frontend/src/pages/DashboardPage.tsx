import React, { useEffect, useState } from "react";
import axios from 'axios';

interface DashboardPageProps {
  logout: () => void;
  backendIP: string;
}

export function DashboardPage({ logout, backendIP }: DashboardPageProps) {
  const [username, setUsername] = useState("");
  const [token, setToken] = useState("");

  useEffect(() => {
    const storageToken = localStorage.getItem("token") || sessionStorage.getItem("token") || "";
    setToken(storageToken);
  }, []);

  useEffect(()=>{
    if(token) dashboard();
  }, [token]);

  const dashboard = async () => {
    try {
      const res = await axios.post('http://' + backendIP + ':8080/api/user/getusername', { token });
      setUsername(res.data);
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <div>
      <h1>Dashboard</h1>
      <h2>Witaj {username}!</h2>
      <button onClick={logout}>Wyloguj</button>
    </div>
  );
}