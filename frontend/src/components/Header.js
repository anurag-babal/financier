import React from 'react'
import '../css/Header.css'
import logo from '../logo/LOGO2.png';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUserLarge, faRightFromBracket } from '@fortawesome/free-solid-svg-icons'

function Header() {
 
    return (
        <div className='header-main'>
            <div className='left-side-part'>
                <img src={logo} className='header-logo' alt="logo*" />
            </div>
            <div className='middle-part'>
                <div className='name'>Aarush Kaswan</div>
            </div>
            <div className='right-side-part'>
                <div className='todays-date'>12/04/2024</div>
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