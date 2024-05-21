import React, {useContext, useEffect} from 'react'
import '../css/Header.css'
import logo from '../logo/LOGO2.png';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUserLarge, faRightFromBracket } from '@fortawesome/free-solid-svg-icons'
import {UserContext} from "../store/user-context";
import {AuthContext} from "../store/auth-context";

function Header() {
    const {userId} = useContext(AuthContext)
    const {getUser, user} = useContext(UserContext);
    const today = new Date();

    useEffect(() => {
        getUser(userId);
    }, []);

    return (
        <div className='header-main'>
            <div className='left-side-part'>
                <img src={logo} className='header-logo' alt="logo*" />
            </div>
            <div className='middle-part'>
                {user && <div className='name'>{user.firstName + ' ' + user.lastName}</div>}
            </div>
            <div className='right-side-part'>
                <div className='todays-date'>{`${today.getDate()}/${today.getMonth()+1}/${today.getFullYear()}`}</div>
                <div className=''>
                    <FontAwesomeIcon icon={faUserLarge} size='lg'/>
                </div>
                <div className=''>
                    <FontAwesomeIcon icon={faRightFromBracket} size='lg'/>
                </div>
            </div>
        </div>
    )
}

export default Header