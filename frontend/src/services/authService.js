import axios from 'axios'

const AUTH_BASE_URL = "http://localhost:8080/api/auth"
const API_URL = "http://localhost:8080/api/v1"

export const signup = (form) => {
    const formData = new FormData(form)
    const data = {}

    formData.forEach((value, key) => {
        data[key] = value
    })

    return axios.post(`${AUTH_BASE_URL}/signup`, data)
}

export const signin = form => {
    const formData = new FormData(form);
    const data = {
        email: formData.get('email'),
        password: formData.get('password')
    }
    return axios.post(`${AUTH_BASE_URL}/signin`, data)
}

export const getUser = async (accessToken) => {
    const headers = {
        'Authorization': `Bearer ${accessToken}`
    }

    try {
        const res = await axios.get(`${API_URL}/user`, null, { headers: headers });

        if (res?.status === 403)
            throw new Error({ data: "TOKEN_EXPIRED" })

        if (res?.status === 200)
            return res?.data
        // else
        //     throw new Error()
    } catch (error) {
        if (error?.response?.status)
            return { status: error?.response?.status }
        else
            return { status: 500 }
    }
}

export const refreshAccessToken = async (token) => {
    try {
        const res = await axios.post(`${AUTH_BASE_URL}/refresh`, { token })
        if (res?.status === 200)
            return { data: res?.data }
    } catch (err) {
        console.log(err?.message);
        return err
    }
}

export const logout = (accessToken) => {
    const headers = {
        'Authorization': `Bearer ${accessToken}`
    }
    return axios.post(`${AUTH_BASE_URL}/logout`, null, { headers: headers })
}