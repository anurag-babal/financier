import React from 'react'
import '../css/Overview.css'
import BarGraph from '../components/BarGraph';

function Overview({ data }) {
    // const data = [
    //     { label: 'A', value: 10 },
    //     { label: 'B', value: 20 },
    //     { label: 'C', value: 15 },
    //     // Add more data points as needed
    // ];
    return (
        <div className='main'>
            <div className='overview-header'>
                Expenses Overview
            </div>
            <div className='overview-body'>
                <div className='stats'>
                    <div>
                        <span className='stats-data value'>₹1,03,500</span>
                        <span className='stats-data'>Total Expenses</span>
                    </div>
                    <div>
                        <span className='stats-data value'>₹14,000</span>
                        <span className='stats-data'>Last Month</span>
                    </div>
                </div>
                <div className='graph'>
                    <BarGraph title="Expenses" data={data} />
                </div>
            </div>
        </div>
    )
}

export default Overview