import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { signin } from '../../services/authService';
import { useDispatch } from 'react-redux';
import { setAuth } from '../../features/authSlice';
import './auth.scss'

const Login = () => {

  const dispatch = useDispatch();

  const navigate = useNavigate();
  const [authError, setAuthError] = useState(true);

  const handleSignIn = (e) => {
    e.preventDefault();
    signin(e.target).then(res => {
      if (res?.status === 200) {
        const { username, email, accessToken, refreshToken } = res?.data;
        dispatch(setAuth({
          user: username,
          email,
          accessToken,
          refreshToken
        }))
        localStorage.setItem("accessToken", accessToken)
        localStorage.setItem("refreshToken", refreshToken)
        navigate("/", { replace: true })
      }
      setAuthError("Can't signin, Try again.")
    })
      .catch(err => setAuthError("Invalid email or password"))
  }

  return (
    <div className="auth-container">
      <form onSubmit={handleSignIn}>
        <h1>Sign In</h1>
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

        <p><button className='sign-up-btn'>Sign In</button></p>
      </form>
    </div>
  )
}

export default Login