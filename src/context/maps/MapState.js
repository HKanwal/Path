import React, { useReducer } from 'react';
import axios from 'axios';

import MapContext from './mapContext';

const MapState = props => {

    axios.defaults.baseURL = 'http://localhost:8080';

    // Get the hazard rating
    const getHazard = async (start, end, carModel) => {
        const config = {
            headers: {
              'Content-Type': 'application/json'
            }
        };
    
        const body = {
            start: start,
            end: end,
            carModel: carModel
        };

        try {
            return await axios.post(`/`, body, config);
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <MapContext.Provider 
            value={{
                getHazard
            }}
        >
            {props.children}
        </MapContext.Provider>
    );
};

export default MapState;