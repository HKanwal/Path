import React, { useReducer } from 'react';
import axios from 'axios';

import MapContext from './mapContext';

const MapState = props => {

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

    const getVType = async () => {
        try {
            return await axios.get('/');
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <MapContext.Provider 
            value={{
                getHazard,
                getVType
            }}
        >
            {props.children}
        </MapContext.Provider>
    );
};

export default MapState;