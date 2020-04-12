import React, { useReducer } from 'react';
import axios from 'axios';
import {
    GET_HAZARD,
    GET_DATA,
    MAP_ERROR
  } from '../types';
import MapContext from './mapContext';

const MapState = props => {

    // Get the hazard rating
    const getHazard = async (curr, dest, carModel) => {
        const config = {
            headers: {
              'Content-Type': 'application/json'
            }
        };
    
        const body = {
            curr: curr,
            dest: dest,
            carModel: carModel
        };

        try {
            await axios.post(`/`, body, config);
        } catch (error) {
            console.log(error);
        }
    };

    const getData = async () => {
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
                getData
            }}
        >
            {props.children}
        </MapContext.Provider>
    );
};

export default MapState;