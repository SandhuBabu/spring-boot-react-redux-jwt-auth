import React from 'react'
import { Outlet, Navigate } from 'react-router-dom'
import { useSelector } from 'react-redux'

const PrivateComponent = ({component}) => {

    const Component = component;
    const user = useSelector(state => state.auth.user)
    return(
        user?
            <Component/>
            :
            <Navigate to="/login" />
    )

}

export default PrivateComponent