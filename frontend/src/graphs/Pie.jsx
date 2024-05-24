import { Pie } from "react-chartjs-2";

function PieChart({ data }) {
    // const labels = ["January", "February", "March", "April", "May", "June", "July"]
    const dataValues = [100, 50, 80, 60, 70, 40, 0]

    const { labels, datasets } = data;

    // const data = {
    //     labels,
    //     datasets: [
    //         {
    //             data: dataValues,
    //             backgroundColor: [
    //                 "rgba(255, 99, 132)",
    //                 "rgba(53, 162, 235)",
    //                 "rgba(255, 206, 86)",
    //                 "rgba(75, 192, 192)",
    //                 "rgba(153, 102, 255)",
    //                 "rgba(255, 159, 64)",
    //                 "rgba(201, 203, 207)",
    //             ],
    //             borderColor: [
    //                 "rgb(255, 99, 132)",
    //                 "rgb(53, 162, 235)",
    //                 "rgb(255, 206, 86)",
    //                 "rgb(75, 192, 192)",
    //                 "rgb(153, 102, 255)",
    //                 "rgb(255, 159, 64)",
    //                 "rgb(201, 203, 207)",
    //             ],
    //             borderWidth: 1,
    //         },
    //     ],
    // }

    console.log('Pie chart data:', data)
    const charData = {
        labels: datasets.label,
        datasets: datasets.map((d, i) => ({
            data: d.data[0],
            backgroundColor: [
                "rgba(255, 99, 132)",
                "rgba(53, 162, 235)",
                "rgba(255, 206, 86)",
                "rgba(75, 192, 192)",
                "rgba(153, 102, 255)",
                "rgba(255, 159, 64)",
                "rgba(201, 203, 207)",
            ],
            borderColor: [
                "rgb(255, 99, 132)",
                "rgb(53, 162, 235)",
                "rgb(255, 206, 86)",
                "rgb(75, 192, 192)",
                "rgb(153, 102, 255)",
                "rgb(255, 159, 64)",
                "rgb(201, 203, 207)",
            ],
            borderWidth: 1,
        })),
    }


    const options = {
        responsive: true,
        maintainAspectRatio: true,
        aspectRatio: 2,
        plugins: {
            legend: {
                position: "top",
            },
            title: {
                display: true,
                text: "Doughnut Chart: Monthly Sales",
            },
        },
    }
    return <Pie data={charData} options={options}/>
}
export default PieChart;