function [C, sigma] = dataset3Params(X, y, Xval, yval)
%DATASET3PARAMS returns your choice of C and sigma for Part 3 of the exercise
%where you select the optimal (C, sigma) learning parameters to use for SVM
%with RBF kernel
%   [C, sigma] = DATASET3PARAMS(X, y, Xval, yval) returns your choice of C and 
%   sigma. You should complete this function to return the optimal C and 
%   sigma based on a cross-validation set.
%

% You need to return the following variables correctly.
C = 1;
sigma = 0.3;

% ====================== YOUR CODE HERE ======================
% Instructions: Fill in this function to return the optimal C and sigma
%               learning parameters found using the cross validation set.
%               You can use svmPredict to predict the labels on the cross
%               validation set. For example, 
%                   predictions = svmPredict(model, Xval);
%               will return the predictions on the cross validation set.
%
%  Note: You can compute the prediction error using 
%        mean(double(predictions ~= yval))
%
%f = @(X1, X2) gaussianKernel(X1, X1, 0.01);
%cSpace = [ 0.01 0.03 0.1 0.3 1 3 10 30];
%sSpace = [ 0.01 0.03 0.1 0.3 1 3 10 30];
%bestPair = [ cSpace(1, 1); sSpace(1, 1) ];
%bestError = 1000000000;
%
%for i = 1:size(cSpace, 2)
%	for j = 1:size(sSpace, 2)
%		curC = cSpace(1, i);
%		curS = sSpace(1, j);
%		f = @(X1, X2) gaussianKernel(X1, X2, curS);
%		model = svmTrain(X, y, curC, f);
%		
%		predictions = svmPredict(model, Xval) ;
%		err = mean(double(predictions ~= yval));
%	
%		if err < bestError
%	    		bestPair = [ curC; curS ];
%	    		bestError = err;
%		end
%	end
%end
%
%C = bestPair(1, 1)
%sigma = bestPair(2, 1)
%bestError


C = 1;
sigma = 0.1;
fprintf('exiting data params\n');


% =========================================================================

end
