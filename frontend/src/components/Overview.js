import React from 'react'
import '../css/Overview.css'
import BarGraph from '../components/BarGraph';
import Dashboard from "./Deashboard";

function Overview({data}) {

    return (
        <>
            <div className={'container my-4'}>
                <div className={'card shadow'}>
                    <div className={'card-header'}>
                        <h4 className={'card-title'}>Expenses Overview</h4>
                    </div>
                    <div className={'card-body'}>
                        <div className={'row'}>
                            <div className={'col-6'}>
                                <Dashboard/>
                            </div>
                            <div className={'col-6'}>
                                <div className={'graph'}>
                                    <BarGraph title="Expenses" data={data}/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Overview