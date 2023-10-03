import { createSlice } from "@reduxjs/toolkit";

const INITIAL_STATE = {
    accessToken: "",
    refreshToken: "",
    user: "",
    email: ""
}

export const authSlice = createSlice({
    name: "auth",
    initialState: INITIAL_STATE,
    reducers: {
        setAuth: (state, {payload}) => {
            state.accessToken = payload.accessToken
            state.refreshToken = payload.refreshToken
            state.user = payload.user
            state.email = payload.email
        }
    }
})

export const {setAuth} = authSlice.actions
export default authSlice.reducer