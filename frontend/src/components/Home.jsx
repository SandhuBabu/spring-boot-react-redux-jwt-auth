import React from 'react'
import { useSelector } from 'react-redux'

const Home = () => {

  const data = useSelector(state => state.auth)

  return (
    <div className='home'>
      <p>User : {data?.user || "No user found"}</p>
      <p>Email : {data?.email || "No email found"}</p>
      <p>Access Token : {data?.accessToken || "No access token found"}</p>
      <p>Refresh Token : {data?.refreshToken || "No refresh token found"}</p>
    </div>
  )
}

export default Home