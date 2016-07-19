# Computes the outliers in a time series of an asteroid's brightness and
# prints them. Outliers must have a Bonferroni value of 10.0 or lower.

args = commandArgs(trailingOnly=TRUE)

file_in = args[1]
first_index = args[2]
last_index = args[3]

data = read.csv(file_in, header=FALSE)
time = data$V1[first_index:last_index]
flux = data$V2[first_index:last_index]

time_squared = time * time

model = lm(flux ~ time + time_squared)
model_predictions = predict(model)

resid = flux - model_predictions
SSE = sum(resid ^ 2)

writeLines("##########\nMODEL\n##########")
summary(model)

writeLines("##########\nERROR\n##########")
paste("Sum squared error: ", SSE)

# Plot
X11()
plot(time, resid)
invisible(readLines("stdin", n=1))