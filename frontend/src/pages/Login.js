import React, {useContext, useState} from 'react';
import '../css/Login.css';
import {Link, useNavigate} from "react-router-dom";
import logo from '../logo/LOGO.png';
import {AuthContext} from "../store/auth-context";

function Login() {
    const {login, isAuthenticated} = useContext(AuthContext)
    const navigate = useNavigate();
    const [showError, setShowError] = useState(false);
    const [errorData, setErrorData] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleOnchange = (inputIdentifier, value) => {
        setShowError(false)
        if (inputIdentifier === "username") {
            setUsername(value);
        } else if (inputIdentifier === "password") {
            setPassword(value);
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!username || !password) {
            setShowError(true);
            setErrorData("Please enter username and password");
        }
        login(username, password)
            .then(() => {
                navigate("/")
            })
            .catch((error) => {
                setShowError(true);
                setErrorData(error.errorMessage);
            });
    }

    return (
        <>
            <div className="login-container">
                <div className='logoPart'>
                    <img src={logo} className='logo' alt="Logo*"/>
                </div>
                <div className="form-container">
                    <form className="form-group" onSubmit={handleSubmit}>
                        <p className="login">Login</p>
                        <br/>
                        <p className="paragraph">Username / Email</p>
                        <p/>
                        <input
                            type="text"
                            placeholder="Username"
                            value={username}
                            onChange={e => handleOnchange('username', e.target.value)}
                        />
                        <p/>
                        <p className="paragraph">Password</p>
                        <p/>
                        <input
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={e => handleOnchange('password', e.target.value)}
                        />
                        <p/>
                        <p>
                            <Link
                                to="/forgot-password"
                                className="forgotPswd"
                            >
                                Forgot Password?
                            </Link>
                        </p>
                        {showError && (
                            <div style={{color: "red", margin: "10px 0"}}>{errorData}</div>
                        )}
                        <button type="submit" className="login-submit-btn">
                            Login
                        </button>
                        <div>
                            <div className="or-Container">
                                <hr className="horizontal-line"/>
                                <p className="or">&nbsp;&nbsp;Or&nbsp;&nbsp;</p>
                                <hr className="horizontal-line"/>
                            </div>
                            <Link to="/" className="signup-btn">
                                Sign Up
                            </Link>
                        </div>
                    </form>
                </div>
            </div>
        </>
    );
}

export default Login;