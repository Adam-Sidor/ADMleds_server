import React, { useState, useEffect } from 'react';
import './App.css';
import { LoginPage } from './pages/LoginPage';
import { DashboardPage } from './pages/DashboardPage';
import { RegisterPage } from './pages/RegisterPage';

function App() {
  const backendIP = 'localhost';

  const [isLogged, setIsLogged] = useState<boolean>(false);
  const [page, setPage] = useState<'login' | 'register'>('login');

  useEffect(() => {
    const token = localStorage.getItem("token") || sessionStorage.getItem("token") || "";
    if (token) setIsLogged(true);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    sessionStorage.removeItem("token");
    setIsLogged(false);
  };

  if (isLogged) {
    return <DashboardPage logout={handleLogout} backendIP={backendIP} />;
  }

  return (
    <div>
      {page === 'login' ? (
        <LoginPage setIsLogged={setIsLogged} backendIP={backendIP} setPage={setPage} />
      ) : (
        <RegisterPage backendIP={backendIP} setPage={setPage} />
      )}
    </div>
  );
}

export default App;
