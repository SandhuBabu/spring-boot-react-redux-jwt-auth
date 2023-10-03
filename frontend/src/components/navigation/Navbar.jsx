import React from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { logout } from '../../services/authService'
import './Navbar.scss'
import { setAuth } from '../../features/authSlice'

const Navbar = () => {

  const data = useSelector(state => state.auth)
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogout = () => {
    const accessToken = localStorage.getItem("accessToken")
    logout(accessToken).then(res => {
      if(res?.status === 200) {
        console.log(res?.data);
        dispatch(setAuth({
          user: '', email: '', accessToken: '', refreshToken: ''
        }))
        localStorage.clear();
        navigate("/login")
      }
    }).catch(err => {
      console.log(err);
    }) 
  }

  return (
    <nav>
      <h1>AuthApp</h1>

      <div>
        {
          data?.user ?
            <>
              <NavLink to="/">Home</NavLink>
            </>
            :
            <>
              <NavLink to="/login">Login</NavLink>
              <NavLink to="/signup">Signup</NavLink>
            </>
        }
      </div>

      {
        data?.user
        && <p>
          {data.user}

          <button onClick={handleLogout} className='logout-btn'>Logout</button>
        </p>
      }

    </nav>
  )
}

export default Navbar