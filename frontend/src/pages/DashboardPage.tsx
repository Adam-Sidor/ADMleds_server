import React from "react";
import axios from 'axios';

export function DashboardPage({logout}:{logout:()=>void}) {
    return (
    <div>
      <h1>Dashboard</h1>
      <button onClick={logout}>Wyloguj</button>
    </div>
  );
}