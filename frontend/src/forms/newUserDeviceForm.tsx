import React, { useEffect} from "react";
import axios from 'axios';

interface NewUserDeviceFormProps {
    backendIP: string;
    deviceId: number;
    token: string;
    closeForm: () => void;
}

export function NewUserDeviceForm({ backendIP, deviceId, token, closeForm}: NewUserDeviceFormProps) {

    const getDeviceTypes = async () => {
        try {
            const res = await axios.post('http://' + backendIP + ':8080/api/usersettings/getalldevices', {});
            console.log(res.data);
        } catch (error) {
            console.log(error);
        }
    }

    const createNewUserDevice = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const form = event.currentTarget;
        const formData = new FormData(form);

        const name = formData.get("name");
        const icon = formData.get("icon");

        const deviceStateId = 1;

        try {
            const res = await axios.post('http://' + backendIP + ':8080/api/usersettings/new', { token, deviceId,deviceStateId,name,icon});
            console.log(res.data);
            if(res.data === 'Zapisano!'){
                form.reset();
                closeForm();
            }
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        getDeviceTypes();
    },[]);

    return (
        <div>
            <h3>Dodaj nowe urządzenie (ID: {deviceId}):</h3>
            <form onSubmit={createNewUserDevice}>
                <input name="name" placeholder="Nazwa" /><br />
                <input name="icon" placeholder="Icon" /><br />
                <input type="submit" value="Stwórz" />
            </form>
        </div>
    );
}