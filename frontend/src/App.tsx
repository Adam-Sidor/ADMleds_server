import React, { useState, useEffect } from 'react';
import './App.css';
import { LoginPage } from './pages/LoginPage';
import { DashboardPage } from './pages/DashboardPage';

function App() {
  const [isLogged, setIsLogged] = useState<boolean>(false);

  useEffect(() => {
    let token = localStorage.getItem("token");
    if(!token) token = sessionStorage.getItem("token");
    if (token) setIsLogged(true);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    sessionStorage.removeItem("token");
    setIsLogged(false);
  };

  return (
    <div>
      {isLogged ? <DashboardPage logout={handleLogout}/> : <LoginPage setIsLogged={setIsLogged}/>}
    </div>
  );
}

export default App;
