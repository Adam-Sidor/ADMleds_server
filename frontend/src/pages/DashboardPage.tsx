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
  const [showColorSelection, setShowColorSelection] = useState<boolean>(false);
  const [brightnessLvl, setBrightnessLvl] = useState(255);

  useEffect(() => {
    const storageToken = localStorage.getItem("token") || sessionStorage.getItem("token") || "";
    setToken(storageToken);
  }, []);

  useEffect(() => {
    if (token) dashboard();
  }, [token]);

  const dashboard = async () => {
    try {
      const res = await axios.post('http://' + backendIP + ':8080/api/user/getusername', { token });
      setUsername(res.data);
    } catch (error) {
      console.log(error);
    }
  }

  const sendRequest = async (commands) => {
    try {
      await axios.post('http://' + backendIP + ':8080/api/sendrequest', { token, commands });
    } catch (error) {
      console.log(error);
    }
  }

  function setMode(event: React.ChangeEvent<HTMLSelectElement>) {
    const selectedMode = event.target.value;
    if (selectedMode === "2") {
      setShowColorSelection(true);
    } else {
      setShowColorSelection(false)
    }
    const commands = {
      "mode": selectedMode
    }
    sendRequest(commands);
  }

  function setColor(event: React.ChangeEvent<HTMLInputElement>) {
    const color = event.target.value;
    const hex = color.replace("#", "");
    const r = parseInt(hex.substring(0, 2), 16);
    const g = parseInt(hex.substring(2, 4), 16);
    const b = parseInt(hex.substring(4, 6), 16);
    const commands = {
      "r": r,
      "g": g,
      "b": b
    }
    sendRequest(commands);
  }

  function setNightMode(isOn: number){
    const commands = {
      "nightmode": isOn
    }
    sendRequest(commands);
  }

  function setBrightness(event: React.PointerEvent<HTMLInputElement>){
    const brightness = event.target.value;
    const commands = {
      "brightness": brightness
    }
    sendRequest(commands);
  }

  function brightnessHandler(event: React.ChangeEvent<HTMLInputElement>){{
    const brightness = event.target.value;
    setBrightnessLvl(parseInt(brightness));
  }}

  function setStatus(isOn: number){
    const commands = {
      "status": isOn
    }
    sendRequest(commands);
  }


  if (page === "settings") {
    return <UserSettingsPage backendIP={backendIP} token={token} goBack={() => setPage("dashboard")} />;
  }

  return (
    <div>
      <h1>Dashboard</h1>
      <h2>Witaj {username}!</h2>
      <button onClick={logout}>Wyloguj</button>
      <button onClick={() => setPage("settings")}>Ustawienia</button><br />
      <h2>Sterowanie oświetleniem</h2>
      <button onClick={()=>setStatus(1)}>Włącz światła</button>
      <button onClick={()=>setStatus(0)}>Wyłącz światła</button><br/>
      <span><select name="Mode" defaultValue="" onChange={setMode}>
        <option value="" disabled>Wybierz tryb</option>
        <option value="0">ARGB</option>
        <option value="1">RGB</option>
        <option value="2">Color</option>
        <option value="3">Thunder</option>
      </select>
      {showColorSelection ? <input type="color" onBlur={setColor}></input> : ""}
      </span><br/>
      <button onClick={()=>setNightMode(1)}>Włącz tryb nocny</button>
      <button onClick={()=>setNightMode(0)}>Wyłącz tryb nocny</button><br/>
      Brightness: {brightnessLvl} <br/>
      <input type="range" name="brightness" min="0" max="255" value={brightnessLvl} onPointerUp={setBrightness} onChange={brightnessHandler}/>
    </div>
  );
}