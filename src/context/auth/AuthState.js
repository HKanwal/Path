import React, { useReducer } from 'react';
import axios from 'axios';

import authReducer from './authReducer';
import setAuthToken from '../../utils/setAuthToken';
import AuthContext from './authContext';
import {
  REGISTER_SUCCESS,
  REGISTER_FAIL,
  USER_LOADED,
  AUTH_ERROR,
  USER_ERROR,
  LOGIN_USER,
  LOGIN_FAILED,
  LOGOUT
} from '../types';

const AuthState = props => {
    const initialState = {
        isAuthenticated: false,
        user: {
          _id: '5e486fe27e9ea246d87561d7'
        },
        username: null,
        current: '5e486fe27e9ea246d87561d7',
        loading: true,
        carModel: 'Volkswagen Beetle'
    };
    
    const [state, dispatch] = useReducer(authReducer, initialState);
    
    // Load User
    const loadUser = async () => {
      if (localStorage.token) {
        setAuthToken(localStorage.token);
      }
  
      try {
        const res = await axios.get('/user');
        dispatch({ type: USER_LOADED, payload: res.data });
      } catch (error) {
        dispatch({ type: AUTH_ERROR });
      }
    };
  
    const getUsername = async quid => {
      try {
        return await axios.get(`/api/users/${quid}`).then(res => {
          return res.data.username;
        });
      } catch (error) {
        dispatch({ type: USER_ERROR });
      }
    };
  
    // Log user in
    const login = async formData => {
      const config = {
        headers: {
          'Content-Type': 'application/json'
        }
      };
  
      try {
        const res = await axios.post('/login', formData, config);
        dispatch({
          type: LOGIN_USER,
          payload: res.data
        });
  
        loadUser();
      } catch (error) {
        dispatch({ type: LOGIN_FAILED });
      }
    };
  
    // Register User
    const register = async formData => {
      const config = {
        headers: {
          'Content-Type': 'application/json'
        }
      };
  
      try {
        const res = await axios.post('/user', formData, config);
        dispatch({
          type: REGISTER_SUCCESS,
          payload: res.data
        });
        //  try to load the user after they are registered.
        loadUser();
      } catch (error) {
        dispatch({
          type: REGISTER_FAIL,
          payload: error.response.data.msg
        });
      }
    };
  
    // Logout
    const logout = () => dispatch({ type: LOGOUT });
  
    return (
      <AuthContext.Provider
        value={{
          user: state.user,
          username: state.username,
          current: state.current,
          isAuthenticated: state.isAuthenticated,
          loading: state.loading,
          carModel: state.carModel,
          loadUser,
          register,
          getUsername,
          login,
          logout
        }}
      >
        {props.children}
      </AuthContext.Provider>
    );
};

export default AuthState;