import { createSlice } from "@reduxjs/toolkit";

const INITIAL_VALUE = {
    status: false
}

export const loadSlice = createSlice({
    name: 'load',
    initialState: INITIAL_VALUE,
    reducers: {
        setLoad: (state, action) => {
            state.status = action.payload.status
        }
    }
})

export const {setLoad} = loadSlice.actions
export default loadSlice.reducer