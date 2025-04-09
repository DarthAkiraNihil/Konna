# Contributing to Konna

## ⚠️ Warning! ⚠️

* All code that is about to be merged must be written according to the codestyle (see [CODESTYLE.md](CODESTYLE.md) for more information). Same for commit messages
* All features should fit project core principles and roadmap.
* In case if the feature is not in the roadmap, then it should be discussed

## Pull requests

* Pull request title should match: ```[#<issue>] <keyword>: <PR summary>```
* Keyword is taken from the Codestyle guide and should be started with a capital letter.
* Pull request title example: ```[#4] Feat: add somethhing feature```
* Pull request description should describe all the work you have done as it will be included in the changelog.
That means you need to include sec, feat, fix and ref commits to the description. However, other commit types are highly appreciated to be in the description.
Also, don't include issue number in the PR description because it is already in the title
* If a PR doesn't meet the codestyle and rules of the PR description or fails checks, it won't be merged until all issues are fixed

## Feature requests

* You want to offer a feature that would be nice in Konna, you need to contact the project owner first.
Of course, you are not forced to do it, but it may save a lot of time on the PR discussion
* The feature will be rejected if it doesn't meet the core principles (see above)

## Documentation

* All added code must be documented with Javadoc comments, but be rational
* Don't need to write docs for trivial methods, such as getters and setters, it their behaviour is common.
However, if a getter or setter does some additional stuff (i.g. reopening a resource), then it should be documented
* 