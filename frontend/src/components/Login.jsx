import React, {useContext} from 'react';
import {AuthContext} from "../store/auth-context";

function Login() {
    const {login} = useContext(AuthContext)

    const handleSubmit = (e) => {
        e.preventDefault();
        const data = new FormData(e.target);
        const username = data.get('username');
        const password = data.get('password');
        console.log(username, password)
        login(username, password)
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">Username</label>
                <input type="text" id="username" name="username" />
                <label htmlFor="password">Password</label>
                <input type="password" id="password" name="password" />
                <button type="submit">Login</button>
            </form>
        </div>
    );
}

export default Login;