import React, { useEffect, useState } from "react";
import axios from 'axios';
import { NewDeviceForm, type DeviceType } from "../forms/newDeviceForm";
import { NewUserDeviceForm } from "../forms/newUserDeviceForm";

interface UserSettingsPageProps {
  backendIP: string;
  token: string;
  goBack: () => void;
}

interface Device {
  deviceId: number;
  ipAddress: string;
  deviceTypeId: number;
}

export function UserSettingsPage({ backendIP, token, goBack }: UserSettingsPageProps) {
  const [samePassAlert, setSamePassAlert] = useState<boolean>(false);
  const [diffPassAlert, setDiffPassAlert] = useState<boolean>(false);
  const [changePasswordStatus, setChangePasswordStatus] = useState("");

  const [showNewDeviceForm, setShowNewDeviceForm] = useState<boolean>(false);
  const [showNewUserDeviceForm,setShowNewUserDeviceForm] = useState<boolean>(false);
  const [newUserDeviceId,setNewUserDeviceId] = useState(0);

  const [devices, setDevices] = useState<Device[]>([]);
  const [deviceTypes, setDeviceTypes] = useState<DeviceType[]>([]);

  const changePassword = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setChangePasswordStatus("");
    const form = event.currentTarget;
    const formData = new FormData(form);

    const password = formData.get("password");
    const newPassword = formData.get("new_password");
    const confirmNewPassword = formData.get("confirm_new_password");

    if (newPassword !== password) {
      setSamePassAlert(false);
    } else {
      setSamePassAlert(true);
    }
    if (newPassword === confirmNewPassword) {
      setDiffPassAlert(false);
      if (newPassword !== password) {
        try {
          const res = await axios.post('http://' + backendIP + ':8080/api/user/changepassword', { password, newPassword, token });
          setChangePasswordStatus(res.data);
          if (res.data === "Zmieniono hasło!") {
            form.reset();
          }
        } catch (error) {
          console.log(error);
        }
      }
    } else {
      setDiffPassAlert(true);
    }

  };

  useEffect(() => {
    getAllDevices();
    getDeviceTypes();
  }, [showNewDeviceForm]);

  const getAllDevices = async () => {
    try {
      const res = await axios.post('http://' + backendIP + ':8080/api/device/getalldevices', {});
      setDevices(res.data);
    } catch (error) {
      console.log(error);
    }
  };

  const getDeviceTypes = async () => {
    try {
      const res = await axios.post('http://' + backendIP + ':8080/api/device/getalltypes', {});
      setDeviceTypes(res.data);
    } catch (error) {
      console.log(error);
    }
  }

  const getUserSettings = async () => {
    try {
      const res = await axios.post('http://' + backendIP + ':8080/api/usersettings/getalldevices', {});
      console.log(res.data);
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <div>
      <h1>Ustawienia</h1>
      <button onClick={goBack}>Wróć</button>
      <h2>Konto:</h2>
      <h3>Zmiana hasła:</h3>
      {changePasswordStatus === "Zmieniono hasło!" ? <span>Zmieniono hasło!<br /></span> : ""}
      <form onSubmit={changePassword}>
        <input type="password" name="password" placeholder="Password" /><br />
        {changePasswordStatus === "Podane hasło jest nieprawidłowe!" ? <span>Podane hasło jest nieprawidłowe!<br /></span> : ""}
        <input type="password" name="new_password" placeholder="New Password" /><br />
        {samePassAlert && <span>Nowe hasło nie może być takie same jak obecne!<br /></span>}
        <input type="password" name="confirm_new_password" placeholder="Confirm New Password" /><br />
        {diffPassAlert && <span>Nowe hasła muszą być takie same!<br /></span>}
        <input type="submit" value="Zmień"></input>
        <input type="reset"></input>
      </form>
      <h2>Urządzenia:</h2>
      <button onClick={() => setShowNewDeviceForm(!showNewDeviceForm)}>+</button>
      {showNewDeviceForm && <NewDeviceForm backendIP={backendIP} />}
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Adres IP</th>
            <th>Typ</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {devices.map((device) => (
            <tr>
              <td>{device.deviceId}</td>
              <td>{device.ipAddress}</td>
              <td>{deviceTypes.map((dType) => (
                dType.deviceTypesId === device.deviceTypeId ? dType.type : ""
              ))}
              </td>
              <td><button onClick={()=>{setShowNewUserDeviceForm(true);setNewUserDeviceId(device.deviceId)}}>+</button></td>
            </tr>
          ))}
        </tbody>
      </table>
      {showNewUserDeviceForm && <NewUserDeviceForm token={token} backendIP={backendIP} deviceId={newUserDeviceId}/>}
      <button onClick={getUserSettings}>Poka moje urządzenia</button>
    </div>
  );
}