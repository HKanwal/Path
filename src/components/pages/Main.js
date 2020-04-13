import React, { useState, useContext, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react';
import { geolocated } from 'react-geolocated';
import MapContext from '../../context/maps/mapContext';
import { Chart } from 'react-charts';
import axios from 'axios';

const Main = ({ hazardRating, google, coords }) => {
    
    let data = [{
        "label": "V_TYPE",
        "data": [[1,82.407150],[5,1.120344],[6,2.851018],[7,1.498413],[8,1.199236],[9,0.377198],[10,0.016050],[11,0.885145],[14,2.128293],[16,0.164413],[17,2.093387],[18,0.052270],[19,0.034300],[20,0.130039],[21,0.014012],[22,0.074669],[23,0.050956]]
    }];

    const mapContext = useContext(MapContext);

    const { getHazard, getVType } = mapContext;

    const series = React.useMemo(
        () => ({
            type: 'bubble',
            showPoints: false
        }),
        []
    )

    const axes = React.useMemo(
        () => [
            { primary: true, type: 'linear', position: 'bottom' },
            { type: 'linear', position: 'left' }
        ],
        []
    )

    const { latitude, longitude } = coords;

    const [pos, setCoordinates] = useState ({
        lat: null,
        long: null
    })

    const { lat, long } = pos;

    const mapStyles = {
        width: '100%',
        height: '100%'
    };

    const onChange = e => 
        setCoordinates({ ...pos, [e.target.name]: e.target.value });


    const [marker, setMarker] = useState ({
        curr: {
            lat: latitude,
            long: longitude
        },
        dest: {
            lat: latitude,
            long: longitude
        }
    });

    const { curr, dest } = marker;

    const [car, setCar] = useState ({
        carModel: ''
    })

    const { carModel } = car;

    const onChangeCar = e => 
        setCar({ ...car, [e.target.name]: e.target.value });


    // const getLocationByLatLng = async (lat, lng) => {
    //     let { data } = await axios.get(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=AIzaSyCdN-PaaKG8Bh5xOhQLpWmAuGNX_0e3b2g`);
    //     return (
    //         data.results[0].address_components.find(c =>
    //           c.types.includes("locality")
    //         ) || {}
    //     ).long_name;
    // }

    const searchForecast = async (lat, lng) =>{
        const { data } = await axios.get(
        `http://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lng}&appid=70235a8c71d9c3c8d7311cb6e30c9016`
        );
        return data;
    }

    const convertWeather = (OWMWeatherID) => {
        let ourWeatherID = 0;
        if(OWMWeatherID ==800) {
            ourWeatherID = 1;
        }else if(OWMWeatherID == 804){
            ourWeatherID = 2
        }else if(OWMWeatherID >- 500 && OWMWeatherID < 600){
            ourWeatherID = 3
        }else if(OWMWeatherID >= 200 && OWMWeatherID < 300){
            ourWeatherID = 3
        }else if(OWMWeatherID >= 600 && OWMWeatherID < 700){
            if(OWMWeatherID == 611 || OWMWeatherID == 612 || OWMWeatherID == 613){
                ourWeatherID = 5;
            }else if(OWMWeatherID == 602 || OWMWeatherID == 622 ){
                ourWeatherID = 6;
            }else{
                ourWeatherID = 4;
            }
        }else if(OWMWeatherID >= 701 && OWMWeatherID <= 762){
            ourWeatherID = 6;
        }else if(OWMWeatherID == 771 || OWMWeatherID == 781){
            ourWeatherID = 7;
        }else {
            ourWeatherID = -1;
        }
        return ourWeatherID;
    }

    const vehicleDataConversion = UserInput => {
        var ourVehicleID = 0;
        UserInput = UserInput.toLowerCase();
        if(UserInput.localeCompare("light duty vehicle")) {
            ourVehicleID = 1;
        }else if(UserInput.localeCompare("panel/cargo van")){
            ourVehicleID = 5;
        }else if(UserInput.localeCompare("other trucks and vans")){
            ourVehicleID = 6;
        }else if(UserInput.localeCompare("unit trucks")){
            ourVehicleID = 7;
        }else if(UserInput.localeCompare("road tractor")){
            ourVehicleID = 8;
        }else if(UserInput.localeCompare("school bus")){
            ourVehicleID = 9;
        }else if(UserInput.localeCompare("smaller school bus")){
            ourVehicleID = 10;
        }else if(UserInput.localeCompare("urban and intercity bus")){
            ourVehicleID = 11;
        }else if(UserInput.localeCompare("motorcycle and moped")){
            ourVehicleID = 14;
        }else if(UserInput.localeCompare("off road vehicles")){
            ourVehicleID = 16;
        }else if(UserInput.localeCompare("bicycle")){
            ourVehicleID = 17;
        }else if(UserInput.localeCompare("purpose-built motorhome")){
            ourVehicleID = 18;
        }else if(UserInput.localeCompare("farm equipment")){
            ourVehicleID = 19;
        }else if(UserInput.localeCompare("construction equipment")){
            ourVehicleID = 20;
        }else if(UserInput.localeCompare("fire engine")){
            ourVehicleID = 21;
        }else if(UserInput.localeCompare("snow mobile")){
            ourVehicleID = 22;
        }else if(UserInput.localeCompare("street car")){
            ourVehicleID = 23;
        }else {
            ourVehicleID  = -1;
        }
        return ourVehicleID;
    }

    const onSubmit = e => {
        setMarker({ ...marker, [e.target.name]: {lat: lat, long: long} });
        searchForecast(latitude, longitude).then(result0 =>{
            searchForecast(dest.lat, dest.lat).then(result => {
                let res0 = convertWeather(result0.weather[0].id);
                let res = convertWeather(result.weather[0].id);
                carModel = vehicleDataConversion(carModel);
                console.log(res0);
                console.log(res);
                getHazard(res0, res, carModel);
            });
        });
    }

    return(
        <div className='grid-ver'>
            <div className='grid-hor-f' style={{ paddingBottom : '2rem' }}>
                <div className='grid-ver-t'>
                    <div style={{ verticalAlign: 'top' }}>
                        <h1 className='text-primary' style={{ fontSize: '3rem', fontWeight: 'bolder' }}>Car Type</h1>
                        <p>(refer to the Data Dictionary)</p>
                        <input
                            type='email'
                            name='carModel'
                            value={carModel}
                            placeholder='e.g., Light Duty Vehicle'
                            onChange={onChangeCar}
                            required
                            style={{ marginLeft: 'auto', textAlign: 'right', width: '95%' }}
                        />
                    </div>
                    <div />
                </div>
                <div className='grid-hor-f-c'>
                    <div className='card-small' style={{ textAlign: 'right', paddingRight: '1rem' }}>
                        <label
                            htmlFor='name'
                            className='login-label'
                        >
                            Latitude
                        </label>
                        <input
                            type='text'
                            name='lat'
                            value={lat}
                            placeholder='e.g., 47.444'
                            onChange={onChange}
                            required
                            style={{ marginLeft: 'auto', textAlign: 'right', width: '70%' }}
                        />
                        <label
                            htmlFor='email'
                            className='login-label'
                            style={{ textAlign: 'right' }}
                        >
                            Longitude
                        </label>
                        <input
                            type='email'
                            name='long'
                            value={long}
                            placeholder='e.g., -122.176'
                            onChange={onChange}
                            required
                            style={{ marginLeft: 'auto', textAlign: 'right', width: '70%' }}
                        />
                        <form onSubmit={onSubmit}>
                            <a href='#'>
                                <input
                                type='submit'
                                value='Set Destination'
                                className='btn btn-primary btn-block'
                                style={{ marginLeft: 'auto', width: '8vw' }}
                                />
                            </a>
                        </form>
                    </div>
                    <div style={{ position: 'relative', width: '32rem', height: '25rem' }}>
                        <Map
                            google={google}
                            zoom={8}
                            style={mapStyles}
                            initialCenter={{ lat: latitude, lng: longitude }}
                            id='map'
                        />
                        <Marker
                            position={curr}
                            name={'curr'}
                            map={document.getElementById('map')}
                        />
                        <Marker 
                            position={dest}
                            name={'dest'}
                            map={document.getElementById('map')}
                        />
                    </div>
                </div>
            </div>
            <div className='grid-hor'>
                <div style={{ left: '-20rem' }}>
                    <h1 className='text-primary' style={{ fontSize: '3rem', fontWeight: 'bolder' }}>Hazard Rating</h1>
                    <body style={{ fontSize: '2rem' }}>{hazardRating}</body>
                </div>
                <div>
                    <h1 className='text-primary' style={{ fontSize: '3rem', fontWeight: 'bolder' }}>Graphical Representation</h1>
                    <div className='grid-hor' style={{
                        width: '32rem',
                        height: '25rem'
                    }}
                    >
                        <h2>Percent Chance of Crash</h2>
                        <Chart data={data} series={series} axes={axes} />
                    </div>
                    <h2>Car Type (Refer to Data Dictionary)</h2>
                </div>
            </div>
        </div>
    );
}

Main.propTypes = {
    hazardRating: PropTypes.number
};

Main.defaultProps = {
    hazardRating: 0
};


export default geolocated({
    positionOptions: {
        enableHighAccuracy: true
    },
    userDecisionTimeout: 5000
})(GoogleApiWrapper({
    apiKey: 'AIzaSyCdN-PaaKG8Bh5xOhQLpWmAuGNX_0e3b2g'
})(Main));