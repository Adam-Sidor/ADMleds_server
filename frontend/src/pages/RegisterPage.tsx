import React, { useState } from "react";
import axios from 'axios';

interface RegisterPageProps {
    backendIP: string;
    setPage?: (page: 'login' | 'register') => void;
}

export function RegisterPage({ backendIP, setPage }: RegisterPageProps) {

    const [registerStatus, setRegisterStatus] = useState("");
    const [usernameAlert, setUsernameAlert] = useState<boolean>(false);
    const [passwordAlert, setPasswordAlert] = useState<boolean>(false);
    const [confirmPasswordAlert, setConfirmPasswordAlert] = useState<boolean>(false);
    const register = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const form = event.currentTarget;
        const formData = new FormData(form);

        const username = formData.get('username')?.toString() || '';
        const password = formData.get('password')?.toString() || '';
        const congirmPassword = formData.get('confirm_password')?.toString() || '';

        if (username === '') {
            setUsernameAlert(true);
        } else {
            setUsernameAlert(false);
        }
        if (password === '') {
            setPasswordAlert(true);
        } else {
            setPasswordAlert(false);
        }
        if (username !== '' && password !== '') {
            if (password === congirmPassword) {
                try {
                    const res = await axios.post('http://' + backendIP + ':8080/api/user/new', { username, password });
                    setRegisterStatus(res.data);
                    if (registerStatus === 'Zapisano!') {
                        form.reset();
                    }

                }
                catch (error) {
                    console.error(error);
                    setRegisterStatus("Wystąpił błąd podczas rejestracji");
                }
                setConfirmPasswordAlert(false);
            } else {
                setConfirmPasswordAlert(true);
            }
        }
    };

    return (
        <div>
            <h1>Rejestracja</h1>
            <form onSubmit={register}>
                <input type="text" id="username" name="username" placeholder="Username"></input><br />
                {usernameAlert && <span>To pole nie może być puste!<br /></span>}
                <input type="password" id="password" name="password" placeholder="Password"></input><br />
                {passwordAlert && <span>To pole nie może być puste!<br /></span>}
                <input type="password" id="confirm_password" name="confirm_password" placeholder="Confirm Password"></input><br />
                {confirmPasswordAlert && <span>Hasła muszą być identyczne<br /></span>}
                {registerStatus && <span>{registerStatus}<br /></span>}
                <input type="submit"></input>
                <input type="reset"></input>
            </form>
            <button onClick={() => setPage && setPage('login')}>Logowanie</button>
        </div>
    );
}
