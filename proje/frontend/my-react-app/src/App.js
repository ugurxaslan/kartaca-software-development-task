import React, { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

const MapComponent = () => {
  const [markers, setMarkers] = useState([]);

  // Özel simgeyi tanımlayın
  const customIcon = new L.Icon({
    iconUrl: 'https://png.pngtree.com/png-vector/20230106/ourlarge/pngtree-flat-red-location-sign-png-image_6553065.png',
    iconSize: [32, 32],
  });

  useEffect(() => {
    
      fetch('http://localhost:8080/api/markers')
        .then(response => response.json())
        .then(data => {
          setMarkers(data); // Markers'ları ayarla
        })
        .catch(error => console.error('Error fetching data:', error));
  }, []);

  return (
    <div>
      <MapContainer center={[39.92, 32.80]} zoom={2} style={{ height: '100vh' }}>
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        />
        
        {markers.map((marker, index) => (
          <Marker key={index} position={marker.position} icon={customIcon}>
            <Popup>{marker.text}</Popup>
          </Marker>
        ))}
      </MapContainer>
      

    </div>
  );
};

export default MapComponent;
