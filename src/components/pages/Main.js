import React, { useState, useContext, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react';
import { geolocated } from 'react-geolocated';
import MapContext from '../../context/maps/mapContext';
import { Chart } from 'react-charts';


const Main = ({ hazardRating, path, google, coords }) => {

    const mapContext = useContext(MapContext);

    const { getHazard, getData } = mapContext;

    let data = [{
        data: [[0,1], [1,2], [2,4], [3,2], [4,7]]
    }];

    useEffect(() => {
        data = getData();
    });

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

    const onSubmit = e => {
        setMarker({ ...marker, [e.target.name]: {lat: lat, long: long} });
        getHazard(curr, dest, carModel);
    }

    return(
        <div className='grid-ver'>
            <div className='grid-hor-f' style={{ paddingBottom : '2rem' }}>
                <div className='grid-ver-t'>
                    <div style={{ verticalAlign: 'top' }}>
                        <h1 className='text-primary' style={{ fontSize: '3rem', fontWeight: 'bolder' }}>Car Model</h1>
                        <input
                            type='email'
                            name='carModel'
                            value={carModel}
                            placeholder='e.g., Volkswagen Beetle'
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
                    <h2>Weather Value</h2>
                </div>
            </div>
        </div>
    );
}

Main.propTypes = {
    hazardRating: PropTypes.number,
    path: PropTypes.string
};

Main.defaultProps = {
    hazardRating: 0,
    path: ''
};


export default geolocated({
    positionOptions: {
        enableHighAccuracy: true
    },
    userDecisionTimeout: 5000
})(GoogleApiWrapper({
    apiKey: 'AIzaSyCdN-PaaKG8Bh5xOhQLpWmAuGNX_0e3b2g'
})(Main));