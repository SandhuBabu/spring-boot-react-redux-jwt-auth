import React from 'react'
import MainLayout from './components/Layout/MainLayout'
import Home from './components/Home'
import Login from './components/authentication/Login'
import Signup from './components/authentication/Signup'
import PrivateComponent from './components/authentication/PrivateComponent'
import AuthLayout from './components/Layout/AuthLayout'

export const appRouter = [
  {
    path: "/",
    element: <MainLayout />,
    children: [
      { index: true, element: <PrivateComponent component={Home} /> },
      { path: "/login", element: <AuthLayout component={Login} />},
      { path: "/signup", element: <AuthLayout component={Signup} /> },
    ]
  }

]

