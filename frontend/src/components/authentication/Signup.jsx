import React, { useState } from 'react'
import { ScrollRestoration, useNavigate } from 'react-router-dom'
import { signup } from '../../services/authService'
import { useDispatch, useSelector } from 'react-redux'
import { setAuth } from '../../features/authSlice'

import './auth.scss'

const Signup = () => {

    const dispatch = useDispatch();
    const user = useSelector(state => state?.auth?.user)

    const navigate = useNavigate();
    const [authError, setAuthError] = useState('');

    const handleSignup = (e) => {
        e.preventDefault()

        signup(e.target).then(res => {
            if (res?.status !== 201) {
                setAuthError("Can't register, Try again.")
                return
            }
            setAuthError(false);

            const {accessToken, refreshToken, username, email} = res?.data
            localStorage.setItem("accessToken", accessToken)
            localStorage.setItem("refreshToken", refreshToken)
            dispatch(setAuth({accessToken, refreshToken, user:username, email}))
            navigate("/")
        })
            .catch(err => {
                if (err.response?.status === 409) {
                    setAuthError(err?.response?.data || "Can't register, Try again.")
                    return
                }
                console.log(err);
                setAuthError("Can't register, Try again.")
            })
    }

    if(user) navigate("/", {replace:true, preventScrollReset: <ScrollRestoration />})

    return (
        <div className="auth-container">
            <form onSubmit={handleSignup}>
                <h1>Sign Up</h1>
                <p>
                    <label htmlFor="firstName">First Name</label>
                    <input type="text" name='firstName' required />
                </p>
                <p>
                    <label htmlFor="lastName">Last Name</label>
                    <input type="text" name='lastName' required />
                </p>
                <p>
                    <label htmlFor="email">Email Address</label>
                    <input type="email" name='email' required />
                </p>
                <p>
                    <label htmlFor="password">Password</label>
                    <input type="password" name='password' required />
                </p>

                {
                    authError &&
                    <p className="auth-error-msg">
                        {authError}
                    </p>
                }

                <p><button className='sign-up-btn'>Sign Up</button></p>
            </form>
        </div>
    )
}

export default Signup