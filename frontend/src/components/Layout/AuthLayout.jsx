import React from 'react'
import { Navigate } from 'react-router-dom'
import { useSelector } from 'react-redux'

const AuthLayout = ({component}) => {

    const user = useSelector(state => state.auth.user)

    const Component = component;

    return (
        user ?
            <Navigate to="/" />
            :
            <Component />
    )
}

export default React.memo(AuthLayout)