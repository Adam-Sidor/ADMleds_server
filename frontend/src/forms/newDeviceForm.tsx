import React from "react";
import axios from 'axios';

interface NewDeviceFormProps {
    backendIP: string;
}

export function NewDeviceForm({ backendIP }: NewDeviceFormProps) {
    const createNewDevice = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const form = event.currentTarget;
        const formData = new FormData(form);

        const ip = formData.get("IP");
        const type = formData.get("type");

        try {
            const res = await axios.post('http://' + backendIP + ':8080/api/device/new', { ip, type });
            console.log(res.data);
            if (res.data === "Zmieniono hasło!") {
                form.reset();
            }
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <div>
            <h3>Dodaj nowe urządzenie:</h3>
            <form onSubmit={createNewDevice}>
                <input name="IP" placeholder="IP" /><br />
                <input type="number" name="type" placeholder="Type" /><br />
                <input type="submit" value="Stwórz" />
            </form>
        </div>
    );
}