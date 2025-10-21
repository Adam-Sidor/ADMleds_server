import React, { useState, useEffect } from 'react';
import './App.css';
import { LoginPage } from './pages/LoginPage';
import { DashboardPage } from './pages/DashboardPage';

function App() {
  const backendIP = 'localhost';

  const [isLogged, setIsLogged] = useState<boolean>(false);

  useEffect(() => {
    const token = localStorage.getItem("token") || sessionStorage.getItem("token") || "";
    if (token) setIsLogged(true);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    sessionStorage.removeItem("token");
    setIsLogged(false);
  };

  return (
    <div>
      {isLogged ? <DashboardPage logout={handleLogout} backendIP={backendIP}/> : <LoginPage setIsLogged={setIsLogged} backendIP={backendIP}/>}
    </div>
  );
}

export default App;
