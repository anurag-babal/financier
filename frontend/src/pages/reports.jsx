import React, {useContext, useEffect, useState} from 'react';
import {Col, Form, Row} from 'react-bootstrap';
import LineChart from "../graphs/Line";
import BarChart from "../graphs/Bar";
import DoughnutChart from "../graphs/Doughnut";
import PieChart from "../graphs/Pie";
import {CategoryContext} from "../store/category-context";
import * as reportService from "../services/report-service";
import {AuthContext} from "../store/auth-context";

export const months = [
    'All', 'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
];
const years = ['All', '2023', '2024', '2025'];
const chartTypes = ['Bar', 'Line', 'Doughnut', 'Pie'];

export default function ReportPage() {
    const {categories} = useContext(CategoryContext);
    const {userId} = useContext(AuthContext);

    const [selectedMonth, setSelectedMonth] = useState(months[0]);
    const [selectedYear, setSelectedYear] = useState(years[0]);
    const [selectedCategory, setSelectedCategory] = useState('all');
    const [selectedChartType, setSelectedChartType] = useState(chartTypes[0]);
    const [reportData, setReportData] = useState(null);

    const handleSelectChange = async (event) => {
        const {name, value} = event.target;
        setSelectedMonth(name === 'month' ? value : selectedMonth);
        setSelectedYear(name === 'year' ? value : selectedYear);
        setSelectedCategory(name === 'category' ? value : selectedCategory);
        setSelectedChartType(name === 'chartType' ? value : selectedChartType);
        await fetchReportData(); // Fetch data on every select change
    };

    const fetchReportData = async () => {
        reportService
            .getExpenseReport(
                userId,
                selectedYear,
                selectedMonth,
                selectedCategory
            )
            .then((r) => {
                setReportData(r.data);
                console.log('Report data:', r.data)
            })
            .catch((error) => {
                console.log('Error fetching report data:', error)
            });
    };

    useEffect(() => {
        fetchReportData(); // Fetch data on initial render and option changes
    }, [selectedMonth, selectedYear, selectedCategory]);

    const renderChart = () => {
        switch (selectedChartType) {
            case 'Bar':
                const data = prepareData(reportData);
                return <BarChart data={data}/>;
            case 'Line':
                return <LineChart data={reportData}/>;
            case 'Doughnut':
                return <DoughnutChart data={reportData}/>;
            case 'Pie':
                if (selectedCategory === 'all' && selectedMonth === 'All' && selectedYear !== 'All') {
                    const data2 = prepareData(reportData);
                    console.log('report chart data:', data2)
                    return <PieChart data={data2}/>;
                }
                break;
            default:
                return null;
        }
    };

    return (
        <div className="container">
            <h1>Reports</h1>
            <Row>
                <Col sm={3}>
                    <Form.Select name="month" value={selectedMonth} onChange={handleSelectChange}>
                        {months.map((month) => (
                            <option key={month} value={month}>
                                {month}
                            </option>
                        ))}
                    </Form.Select>
                </Col>
                <Col sm={3}>
                    <Form.Select name="year" value={selectedYear} onChange={handleSelectChange}>
                        {years.map((year) => (
                            <option key={year} value={year}>
                                {year}
                            </option>
                        ))}
                    </Form.Select>
                </Col>
                <Col sm={3}>
                        <Form.Select name="category" value={selectedCategory} onChange={handleSelectChange}>
                            <option value="all">All Categories</option>
                            {categories.map((category) => (
                                <option key={category.id} value={category.name}>
                                    {category.name}
                                </option>
                            ))}
                        </Form.Select>
                </Col>
                <Col sm={3}>
                    <Form.Select name="chartType" value={selectedChartType} onChange={handleSelectChange}>
                        {chartTypes.map((type) => (
                            <option key={type} value={type}>
                                {type}
                            </option>
                        ))}
                    </Form.Select>
                </Col>
            </Row>
            <br/>
            {/*{reportData && renderChart()}*/}
            {renderChart()}
        </div>
    );
};

export function prepareData(reportData) {
    if (!reportData) return null;

    const labels = reportData.content.map((month) => month.groupBy);
    const categoryData = {};
    const labelData = new Set();

    reportData.content.forEach((category) => {
        category.categoryExpenses.forEach((expense) => {
            labelData.add(expense.category); // Add category to Set for uniqueness
            if (!categoryData[expense.category]) {
                categoryData[expense.category] = [];
            }
            categoryData[expense.category].push(expense.total);
        });
    });

    const datasets = Array.from(labelData).map((label, index) => {
        return {
            label,
            data: categoryData[label],
            backgroundColor: `rgba(${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)})`,
            borderColor: `rgba(${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)})`,
            borderWidth: 1,
        };
    });

    return { labels, datasets };
}
