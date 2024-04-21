import React, { Component } from 'react';
import Chart from 'chart.js/auto';

class BarGraph extends Component {
    chartRef = React.createRef();  

    componentDidMount() {
        this.buildChart();
    }

    buildChart = () => {
        const myChartRef = this.chartRef.current.getContext("2d");

        if (this.chartInstance) {
            this.chartInstance.destroy();
        }

        this.chartInstance = new Chart(myChartRef, {
            type: 'bar',
            data: {
                labels: this.props.data.map(item => item.label),
                datasets: [{
                    label: this.props.title,
                    data: this.props.data.map(item => item.value),
                    backgroundColor: 'rgba(75, 192, 192, 0.6)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    render() {
        return (
            <div>
                <canvas ref={this.chartRef} />
            </div>
        );
    }
}

export default BarGraph;
