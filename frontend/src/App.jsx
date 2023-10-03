import { useEffect } from "react";
import { useRoutes, useNavigate } from "react-router-dom"
import { appRouter } from "./router"
import { getUser, refreshAccessToken } from "./services/authService";
import { useDispatch } from "react-redux";
import { setAuth } from "./features/authSlice";

function App() {

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const accessToken = localStorage.getItem("accessToken");
  const refreshToken = localStorage.getItem("refreshToken");

  const fetchUser = async () => {
    try {
      const res = await getUser()

      if (res?.username) {
        dispatch(setAuth({
          user: res.username,
          email: res.email,
          accessToken,
          refreshToken
        }));
      }

      if (res?.status === 403) {
        handleRefresh()
        return
      }

      if (res?.status === 500) {
        console.log("Sorry, server is not responding at this time.");
        return
      }
    } catch (err) {
      console.log(err);
    }
  }

  const handleRefresh = async () => {
    try {
      const res = await refreshAccessToken(refreshToken);
      if (res?.data) {
        dispatch(setAuth({
          user: res?.data?.username,
          email: res?.data?.email,
          accessToken: res?.data?.accessToken,
          refreshToken: res?.data?.refreshToken
        }))

        localStorage.setItem("accessToken", res?.data?.accessToken)
        localStorage.setItem("refreshToken", res?.data?.refreshToken)
        return navigate("/")
      }


      return navigate("/login")
    } catch (error) {


      console.log(err);
    }
  }

  useEffect(() => {
    accessToken && fetchUser();
    // console.clear()
  }, [])

  return useRoutes(appRouter)
}

export default App