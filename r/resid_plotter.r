# Generates a plot of a the residual of a linear model of brightness from a time, flux that uses a 2nd
# degree polynomial. Allows for restriction of data to account for jumps in flux values. 

args = commandArgs(trailingOnly=TRUE)

file_in = args[1]
first_index = strtoi(args[2])
last_index = strtoi(args[3])

data = read.csv(file_in, header=FALSE)
time = data$V1[first_index:last_index]
flux = data$V2[first_index:last_index]
time_squared = time * time

model = lm(flux ~ time + time_squared)
model_predictions = predict(model)
resid = flux - model_predictions

# Plot
X11()
plot(time, resid)
invisible(readLines("stdin", n=1))