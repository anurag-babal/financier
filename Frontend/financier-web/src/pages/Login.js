import React, { useState } from 'react';
import '../css/Login.css';
import { Link } from "react-router-dom";
import logo from '../logo/LOGO.png';
function Login() {

  return (
  <>
    <div className="login-container">
        <div className='logoPart'>
            <img src={logo} className='logo' alt="Logo*" />
        </div>
        <div className="form-container">
            <form className="form-group">
                <p className="login">Login</p>
                <br />
                <p className="paragraph">Username / Email</p>
                <p />
                <input
                type="text"
                placeholder="Username"
                />
                <p />
                <p className="paragraph">Password</p>
                <p />
                <input
                type="password"
                placeholder="Password"
                />
                <p />
                <p>
                <Link
                    to="/forgot-password"
                    className="forgotPswd"
                >
                    Forgot Password?
                </Link>
                </p>
                {/* {showError && (
                <div style={{ color: "red", margin: "10px 0" }}>{errorData}</div>
                )} */}
                <Link to="/homepage">
                    <button type="submit" className="login-submit-btn">
                        Login
                    </button>
                </Link>
                <div>
                    <div className="or-Container">
                    <hr className="horizontal-line" />
                    <p className="or">&nbsp;&nbsp;Or&nbsp;&nbsp;</p>
                    <hr className="horizontal-line" />
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
};

export default Login;