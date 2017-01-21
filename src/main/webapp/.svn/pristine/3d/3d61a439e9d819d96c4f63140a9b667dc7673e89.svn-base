/**
 * Created by jwm_000 on 2015-10-06.
 */

var SimpleBarChart = (function (window, d3, undefined){

    var padding = {left: 4, right: 4, top: 14, bottom: 14};
    var width = 160;
    var height = 240;
    var fills = ["#f76869", "#5da2e6", "#00bf88", "#f4c100", "#bf5bf3", "#3eb5ac" , "#f58410", "#f94a76", "#97d54c", "#f0a0dd" ,
        "#b2485f", "#9b9c9a", "#49ddff", "#306555", "#003366", "#7fbcff", "#ba8d72", "#ba8d72", "#8c7721", "#6db5a1"];

    var categoryField = "label";
    var valueField = "value";
    var maxField = "max";
    var avgField = "avg";

    var SimpleBarChart = function () {
        this.options = {};

        this.draw = function (bAnimation) {

            var options = this.options;
            options.categoryField = options.categoryField || categoryField;
            options.valueField = options.valueField || valueField;
            options.maxField = options.maxField || maxField;
            options.avgField = options.avgField || avgField;
            if (options.showDataTip === undefined){
                options.showDataTip = true;
            }

            // [{label:'점수', value:4, avg:5, max:10 }, {label:'순위', value:1, max:31 }]
            var data = options.data;

            options.width = options.width || width;
            options.height = options.height || height;
            options.fills = options.fills || fills;

            var innerWidth = options.width - padding.left - padding.right;
            var innerHeight = options.height - padding.top - padding.bottom;

            var xScale = d3.scale.ordinal()
                .rangeRoundBands([0, innerWidth], 0.5);

            var xScaleBackground = d3.scale.ordinal()
                .rangeRoundBands([0, innerWidth], 0.25);

            var xAxis = d3.svg.axis()
                .scale(xScale)
                .orient("bottom")
                .tickSize(0);

            var color = d3.scale.ordinal()
                .range(options.fills);

            var holder = options.contentHolder;
            var svg = d3.select(holder).append("svg")
                .attr("width", options.width)
                .attr("height", options.height);


            var categoryNames = data.map(function (d) {
                return d[options.categoryField];
            });

            xScale.domain(categoryNames);
            xScaleBackground.domain(categoryNames);

            var viewport = svg.append("g")
                .attr("transform", "translate(" + padding.left + ","
                + padding.top + ")");

            var barWidth = xScale.rangeBand();
            var backgroundWidth = xScaleBackground.rangeBand();

            var d3Tooltip = d3.select(".tip");
            if (d3Tooltip.empty()) {
                d3Tooltip = d3.select("body")
                    .append("div")
                    .attr("class", "tip");
            }

            var yScale = d3.scale.linear()
                .rangeRound([innerHeight, 0]);

            var viewData = viewport.selectAll("rect")
                .data(data);

            if (bAnimation) {
                viewData.enter()
                    .append("rect")
                    .attr("width", backgroundWidth)
                    .attr("height", 0)
                    .attr("x", function (d, i) {
                        return xScale.range()[i] - ((backgroundWidth - barWidth) / 2);
                    })
                    .attr("y", innerHeight)
                    .style("fill", function (d, i) {
                        if (options.backgroundColorFunction) {
                            return options.backgroundColorFunction(d, i);
                        }
                        return "#DEE5EC";
                    })
                    .transition()
                    .duration(1000)
                    .attr("y", function (d, i) {
                        if (d[options.avgField]) {
                            yScale.domain([0, d[options.maxField]]);
                            var barHeight = innerHeight - yScale(d[options.avgField]);
                            return innerHeight - barHeight;
                        } else {
                            return innerHeight;
                        }

                    })
                    .attr("height", function (d, i) {
                        if (d[options.avgField]) {
                            yScale.domain([0, d[options.maxField]]);
                            var barHeight = innerHeight - yScale(d[options.avgField]);
                            return barHeight;
                        } else {
                            return 0;
                        }
                    });
            }else {
                viewData.enter()
                    .append("rect")
                    .attr("x", function (d, i) {
                        return xScale.range()[i] - ((backgroundWidth - barWidth) / 2);
                    })
                    .attr("y", function (d, i) {
                        if (d[options.avgField]) {
                            yScale.domain([0, d[options.maxField]]);
                            var barHeight = innerHeight - yScale(d[options.avgField]);
                            return innerHeight - barHeight;
                        } else {
                            return innerHeight;
                        }

                    })
                    .attr("width", backgroundWidth)
                    .attr("height", function (d, i) {
                        if (d[options.avgField]) {
                            yScale.domain([0, d[options.maxField]]);
                            var barHeight = innerHeight - yScale(d[options.avgField]);
                            return barHeight;
                        } else {
                            return 0;
                        }
                    })
                    .style("fill", function (d, i) {
                        if (options.backgroundColorFunction) {
                            return options.backgroundColorFunction(d, i);
                        }
                        return "#DEE5EC";
                    })
            }

                if (options.showDataTip) {
                    viewData.on("mousemove", function (d, i) {
                            var x = d3.event.pageX;
                            var y = d3.event.pageY;

                            var tooltipHeight = parseFloat(d3Tooltip.style("height"));
                            var tooltipWidth = parseFloat(d3Tooltip.style("width"));
                            var tooltip = d3Tooltip.node();

                            d3Tooltip.style("left", x - tooltipWidth / 2 + "px")
                                .style("top", y - tooltipHeight - 30 + "px")
                                .style("visibility", "visible");

                            if (options.backgroundTipFunction) {
                                options.backgroundTipFunction(d, i, tooltip);
                            } else {
                                //d3Tooltip.text(d[options.valueField]);
                            }
                        })
                        .on("mouseout", function () {
                            d3Tooltip.style("visibility", "hidden");
                        });
                }

            if (bAnimation) {
                viewData.enter()
                    .append("rect")
                    .attr("width", barWidth)
                    .attr("height", 0)
                    .attr("x", function (d, i) {
                        return xScale.range()[i];
                    })
                    .attr("y", innerHeight)
                    .style("fill", function (d, i) {
                        if (options.colorFunction) {
                            return options.colorFunction(d, i);
                        }
                        return color(i);
                    })
                    .style("fill-opacity", .8)
                    .transition()
                    .duration(1000)
                    .attr("y", function (d, i) {
                        yScale.domain([0, d[options.maxField]]);
                        var barHeight = innerHeight - yScale(d[options.valueField]);
                        return innerHeight - barHeight;
                    })
                    .attr("height", function (d, i) {
                        yScale.domain([0, d[options.maxField]]);
                        var barHeight = innerHeight - yScale(d[options.valueField]);
                        return barHeight;
                    })
                    .each("end", function () {
                        viewData.enter()
                            .append("text")
                            .text(function (d, i) {
                                if (options.labelFunction) {
                                    return options.labelFunction(d, i);
                                } else {
                                    return "";
                                }
                            })
                            .style("fill", function (d, i) {
                                if (options.colorFunction) {
                                    return options.colorFunction(d, i);
                                }
                                return color(i);
                                //return "#000000";
                            })
                            .attr("x", function (d, i) {
                                var textWidth = this.getComputedTextLength();
                                //console.log(this);
                                var textX = (barWidth - textWidth) / 2;
                                return xScale.range()[i] + textX;
                            })
                            .attr("y", function (d, i) {
                                yScale.domain([0, d[options.maxField]]);
                                var barHeight = innerHeight - yScale(d[options.valueField]);
                                return innerHeight - barHeight - 4;
                            });


                    });
            }else {
                viewData.enter()
                    .append("rect")
                    .attr("x", function (d, i) {
                        return xScale.range()[i];
                    })
                    .attr("y", function (d, i) {
                        yScale.domain([0, d[options.maxField]]);
                        var barHeight = innerHeight - yScale(d[options.valueField]);
                        return innerHeight - barHeight;
                    })
                    .attr("width", barWidth)
                    .attr("height", function (d, i) {
                        yScale.domain([0, d[options.maxField]]);
                        var barHeight = innerHeight - yScale(d[options.valueField]);
                        return barHeight;
                    })
                    .style("fill", function (d, i) {
                        if (options.colorFunction) {
                            return options.colorFunction(d, i);
                        }
                        return color(i);
                    });
                viewData.enter()
                    .append("text")
                    .text(function (d, i) {
                        if (options.labelFunction) {
                            return options.labelFunction(d, i);
                        } else {
                            return "";
                        }
                    })
                    .style("fill", function (d, i) {
                        if (options.colorFunction) {
                            return options.colorFunction(d, i);
                        }
                        return color(i);
                        //return "#000000";
                    })
                    .attr("x", function (d, i) {
                        var textWidth = this.getComputedTextLength();
                        //console.log(this);
                        var textX = (barWidth - textWidth) / 2;
                        return xScale.range()[i] + textX;
                    })
                    .attr("y", function (d, i) {
                        yScale.domain([0, d[options.maxField]]);
                        var barHeight = innerHeight - yScale(d[options.valueField]);
                        return innerHeight - barHeight - 4;
                    });

            }

            if (options.showDataTip) {
                viewData.on("mousemove", function (d, i) {
                    var x = d3.event.pageX;
                    var y = d3.event.pageY;

                    var tooltipHeight = parseFloat(d3Tooltip.style("height"));
                    var tooltipWidth = parseFloat(d3Tooltip.style("width"));
                    var tooltip = d3Tooltip.node();

                    d3Tooltip.style("left", x - tooltipWidth / 2 + "px")
                        .style("top", y - tooltipHeight - 30 + "px")
                        .style("visibility", "visible");

                    if (options.dataTipFunction) {
                        options.dataTipFunction(d, i, tooltip);
                    } else {
                        //d3Tooltip.text(d[options.valueField]);
                    }
                })
                    .on("mouseout", function () {
                        d3Tooltip.style("visibility", "hidden");
                    });
            }

            // x 축
            var xAxisGroup = viewport.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + innerHeight + ")")
                .call(xAxis);

            xAxisGroup.selectAll("text")
                .attr("dy", 10);

        }
    }

    return SimpleBarChart;
})(window, d3);
