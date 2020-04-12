import axios from 'axios';

const setAuthToken = token => {
  // Checks if there is a token passed in (user logged in, loaded), if there is, sets that to the default header for 'x-auth-token in the axios calls, otherwise deletes it.
  if (token) {
    axios.defaults.headers.common['x-auth-token'] = token;
  } else {
    delete axios.defaults.headers.common['x-auth-token'];
  }
};

export default setAuthToken;
