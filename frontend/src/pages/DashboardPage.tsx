import { useEffect, useState } from "react";
import axios from 'axios';
import { UserSettingsPage } from "./UserSettingsPage";

interface DashboardPageProps {
  logout: () => void;
  backendIP: string;
}

export function DashboardPage({ logout, backendIP }: DashboardPageProps) {
  const [username, setUsername] = useState("");
  const [token, setToken] = useState("");
  const [page, setPage] = useState<"dashboard" | "settings">("dashboard");

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

  if (page === "settings") {
    return <UserSettingsPage backendIP={backendIP} token={token} goBack={() => setPage("dashboard")} />;
  }

  return (
    <div>
      <h1>Dashboard</h1>
      <h2>Witaj {username}!</h2>
      <button onClick={logout}>Wyloguj</button>
      <button onClick={() => setPage("settings")}>Ustawienia</button>
    </div>
  );
}