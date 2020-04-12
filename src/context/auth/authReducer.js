import {
    REGISTER_SUCCESS,
    USER_LOADED,
    LOGIN_USER,
    LOGOUT
  } from '../types';
  
  export default (state, action) => {
    console.log(action.type);
    switch (action.type) {
      case REGISTER_SUCCESS:
      case LOGIN_USER:
        localStorage.setItem('token', action.payload.token);
        return {
          ...state,
          ...action.payload,
          isAuthenticated: true,
          loading: false
        };
      case LOGOUT:
        return {
          ...state,
          isAuthenticated: false,
          loading: true,
          user: null,
          current: null,
          username: null
        };
      case USER_LOADED:
        return {
          ...state,
          isAuthenticated: true,
          loading: false,
          user: action.payload
        };
      default:
        return state;
    }
  };
  