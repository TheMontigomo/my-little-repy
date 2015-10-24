from django.shortcuts import render
from django.http import HttpResponse
from django.template import Context, loader

def show(request):
	name = "Question pattern"
	size = 6
	template = loader.get_template('question.html')
	context = Context({'size' : size})
	return HttpResponse(template.render(context))
