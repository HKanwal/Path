import React, { useState, useContext } from 'react';
import logo from './safety-guy.png';
import check from './checkmark.png';
import { Redirect } from 'react-router-dom';

import AuthContext from '../../context/auth/authContext';


const Auth = () => {

    const authContext = useContext(AuthContext);

    const { login, isAuthenticated, register } = authContext;

    const [user, setUser] = useState({
        email: '',
        password: ''
    });
    
    const { email, password } = user;

    const onChangeLog = e => setUser({ ...user, [e.target.name]: e.target.value });

    const onSubmitLog = e => {
        e.preventDefault();
        if (email === '' || password === '') {
            console.log('Please fill in all fields');
        } else {
            login({
                email,
                password
            });
        }
    }

    const [newUser, setRegInfo] = useState({
        newEmail: '',
        name: '',
        newPassword: '',
        confirmPassword: '',
        model: ''
    });

    const { newEmail, name, newPassword, confirmPassword, model } = newUser;

    const onChangeReg = e =>
        setRegInfo({ ...newUser, [e.target.name]: e.target.value });

    const onSubmitReg = e => {
        e.preventDefault();
        if (newPassword !== confirmPassword) {
            //   Will be an alert
            return console.log('Please enter the same password twice.');
        } else if (
            newEmail === '' ||
            name === '' ||
            newPassword === '' ||
            confirmPassword === '' ||
            model === ''
        ) {
            console.log('Please fill out all fields.');
        }else {
            register({
                email,
                name,
                password,
                model
              });
        }
    }
    if(!isAuthenticated){
        return(
            <div className='grid-hor'>
                <div className='card-nh-auth-log'>
                    <div className='grid-hor'>
                        <div style={{ textAlign: 'left' }}>
                            <h1 className='text-primary' style={{ fontSize: '3.5rem', font: 'Candal', fontWeight: 'bolder' }}>Login</h1>
                        </div>
                        <div>
                        </div>
                    </div>
                    <div className='grid-hor'>
                        <div style={{ maxWidth: '20vw', textAlign: 'center' }}>
                            <img src={logo} style={{ maxWidth: '70%' }} alt="Logo"/>
                        </div>
                        <div style={{ verticalAlign: 'center' }}>
                            <div style={{ textAlign: 'right' }}>
                                <form onSubmit={onSubmitLog}>
                                    <div>
                                    <label
                                        htmlFor='email'
                                        className='login-label'
                                    >
                                        Email
                                    </label>
                                    <input
                                        type='email'
                                        name='email'
                                        value={email}
                                        onChange={onChangeLog}
                                        required
                                        style={{ marginLeft: 'auto', width: '14vw' }}
                                    />
                                    </div>
                                    <div>
                                    <label
                                        htmlFor='password'
                                        className='login-label'
                                    >
                                        Password
                                    </label>
                                    <input
                                        type='password'
                                        name='password'
                                        value={password}
                                        onChange={onChangeLog}
                                        required
                                        style={{ marginLeft: 'auto', width: '14vw' }}
                                    />
                                    </div>
                                    <div>
                                        {/* eslint-disable-next-line */}
                                        <a href='#'>
                                            <input
                                            type='submit'
                                            value='Login'
                                            className='btn btn-primary btn-block'
                                            style={{ marginLeft: 'auto', width: '5vw' }}
                                            />
                                        </a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <div className='card-nh-auth'>
                        <div className='grid-hor'>
                            <div style={{ textAlign: 'left' }}>
                                <h1 className='text-primary' style={{ fontSize: '3.5rem' }}>Register</h1>
                            </div>
                            <div style={{ textAlign: 'right' }}>
                                <label
                                    htmlFor='email'
                                    className='login-label'
                                    style={{ marginLeft: 'auto', textAlign: 'right' }}
                                >
                                    Email:
                                </label>
                                <input
                                    type='email'
                                    name='email'
                                    value={newEmail}
                                    placeholder='john@gmail.com'
                                    onChange={onChangeReg}
                                    required
                                    style={{ marginLeft: 'auto', textAlign: 'right' }}
                                />
                            </div>
                        </div>
                        <div className='grid-hor'>
                            <div>
                                <img src={check} style={{ maxWidth: '70%' }} alt="Logo"/>
                            </div>
                            <div style={{ verticalAlign: 'center' }}>
                                <div style={{ textAlign: 'right' }}>
                                    <form onSubmit={onSubmitReg}>
                                        
                                        <label
                                            htmlFor='name'
                                            className='login-label'
                                            style={{ marginLeft: 'auto' }}
                                        >
                                            Name:
                                        </label>
                                        <input
                                            type='text'
                                            name='name'
                                            value={name}
                                            placeholder='Johnny Appleseed'
                                            onChange={onChangeReg}
                                            required
                                            style={{ marginLeft: 'auto', textAlign: 'right' }}
                                        />
                                        <label
                                            htmlFor='email'
                                            className='login-label'
                                            style={{ marginLeft: 'auto' }}
                                        >
                                            Password:
                                        </label>
                                        <input
                                            type='password'
                                            name='password'
                                            value={newPassword}
                                            onChange={onChangeReg}
                                            required
                                            style={{ marginLeft: 'auto', textAlign: 'right' }}
                                        />
                                        <label
                                            htmlFor='email'
                                            className='login-label'
                                            style={{ marginLeft: 'auto' }}
                                        >
                                            Confirm Password:
                                        </label>
                                        <input
                                            type='password'
                                            name='confirmPassword'
                                            value={confirmPassword}
                                            onChange={onChangeReg}
                                            required
                                            style={{ marginLeft: 'auto', textAlign: 'right' }}
                                        />
                                        <label
                                            htmlFor='email'
                                            className='login-label'
                                            style={{ marginLeft: 'auto' }}
                                        >
                                            Car Model
                                        </label>
                                        <input
                                            type='email'
                                            name='model'
                                            value={model}
                                            onChange={onChangeReg}
                                            required
                                            style={{ marginLeft: 'auto', textAlign: 'right' }}
                                        />
                                        <a href='#'>
                                            <input
                                            type='submit'
                                            value='Register'
                                            className='btn btn-block btn-primary'
                                            style={{ marginLeft: 'auto', width: '5vw' }}
                                            />
                                        </a>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
        );
    } else
        return <Redirect to='/' />;
}

export default Auth;