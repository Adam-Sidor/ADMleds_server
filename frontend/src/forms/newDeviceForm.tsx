import React, { useEffect, useState } from "react";
import axios from 'axios';
import { type UserDevice } from "../pages/UserSettingsPage";

interface NewDeviceFormProps {
    backendIP: string;
    userDevices: UserDevice[];
    token: string;
}

export interface DeviceType {
    deviceTypesId: number;
    type: string;
    description: string;
}

export function NewDeviceForm({ backendIP, userDevices, token }: NewDeviceFormProps) {
    const [newDeviceStatus, setNewDeviceStatus] = useState("");
    const [deviceTypes, setDeviceTypes] = useState<DeviceType[]>([]);
    const [isDevice, setIsDevice] = useState(false);
    const getDeviceTypes = async () => {
        try {
            const res = await axios.post('http://' + backendIP + ':8080/api/device/getalltypes', {});
            setDeviceTypes(res.data);
        } catch (error) {
            console.log(error);
        }
    }

    const createNewDevice = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const form = event.currentTarget;
        const formData = new FormData(form);

        const ip = formData.get("IP");
        const type = formData.get("type");



        userDevices.map((uDevice) => (
            uDevice.deviceIp === ip ? setIsDevice(true) : ""
        ));
        if (!isDevice) {
            try {
                const res = await axios.post('http://' + backendIP + ':8080/api/device/new', { ip, type });
                console.log(res.data);
                setNewDeviceStatus(res.data);
            } catch (error) {
                console.log(error);
            }
        } else {
            setIsDevice(false);
            console.log("urządzenie istnieje");
            try {
                await axios.post(`http://${backendIP}:8080/api/usersettings/updatestate`, {
                    token,
                    deviceIp: ip,
                    newState: "added"
                });
            } catch (error) {
                console.error(error);
            }
        }

    }

    useEffect(() => {
        getDeviceTypes();
    }, []);

    return (
        <div>
            <h3>Dodaj nowe urządzenie:</h3>
            <form onSubmit={createNewDevice}>
                <input name="IP" placeholder="IP" /><br />
                <select name="type" defaultValue="">
                    <option value="" disabled>Wybierz typ urządzenia</option>
                    {deviceTypes.map((device) => (
                        <option key={device.deviceTypesId} value={device.deviceTypesId}>
                            {device.type}
                        </option>
                    ))}
                </select><br />
                {newDeviceStatus}
                <input type="submit" value="Stwórz" />
            </form>
        </div>
    );
}